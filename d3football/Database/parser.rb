require 'rubygems'
require 'nokogiri'
require 'open-uri'

require_relative 'config/boot'
require_relative 'models/team'
require_relative 'models/game'
require_relative 'models/drive'
require_relative 'models/play'
require_relative 'models/punt'
require_relative 'models/goforit'
require_relative 'models/field_goal'
ActiveRecord::Base.logger.level = 1 # or Logger::INFO
def normalize(str)
  return str.gsub(/(\n\r)|\s+/, ' ').strip
end

def processPlayByPlay(filename)
  suppress(Exception) do
  page = Nokogiri::HTML(open(filename))
  # extract the names of the teams
  puts '----------------'
  puts page.css('div.align-center').class
  puts page.css('div.align-center')[0].class
  puts '=================='
  roadteam, hometeam = page.css('div.align-center')[0].css('div')[0].text.split(' vs. ')
  if roadteam == nil
	puts"roadteam is nil"
	end
  if hometeam == nil
	puts "hometeam is nil"
	end
# puts "#{roadteam} at #{hometeam}"
  # extract the score
  roadscore = page.css('span.stats-header')[1].text
  homescore = page.css('span.stats-header')[3].text
    if roadscore == nil || homescore == nil
	puts"the score is nil"
	end
 # puts "roadscore: #{roadscore}, homescore: #{homescore}"
  # extract the shortened team names
  roadteamshort = page.css('span.stats-header')[0].text
   if roadteamshort ==nil
	puts"roadteamshort is nil"
   end
  hometeamshort = page.css('span.stats-header')[2].text
   if hometeamshort == nil
	puts"hometeamshort is nil"
   end
  puts "shortened: #{roadteamshort} at #{hometeamshort}"
  roadteam = roadteam.upcase
  hometeam = hometeam.upcase
  m = filename.match(/_(\w+).xml/)
  gamecode = m.captures[0]
  date = filename.match(/(\d+)......xml/)[1]
  @home = Team.find_or_create_by(school: hometeam)
  @driveteam = @home
  @road = Team.find_or_create_by(school: roadteam)
  # TODO: get real date, etc. Update score 
  @game = Game.find_or_create_by(home_id: @home.id, road_id: @road.id, home_score: homescore, road_score: roadscore, date: date, gamecode: gamecode)
  @quarter = 1
  @yardline = 50
  @drivepoints = 0
  @drive = Drive.find_or_create_by(game: @game, team: @home, quarter: 1, starttime: '15:00', location: @yardline, points: @drivepoints)
  @playnum = 1
   
  # zai zhe li

  firstteam =normalize(page.css('tr.odd')[0].text)
  roadteamini = firstteam.match(/(^.*) and (\d+) at ([A-Z]+)/i)[3] 
  # search for the table rows using a JQuery-like syntax
  page.css('tr.odd, tr.even').each do |e|  
    # check if there is a "summary bold" class
    summary = e.css('td[class="summary bold"]')
    if summary.length == 1
      # This happens at the end of a drive!
      #puts summary.text
      @playnum = 1
    else
      # get first td, which is down/distance
      # then second td, which is event
      downdist=normalize(e.css('td')[0].text)
      event=normalize(e.css('td')[1].text)
      #printf("%s => %s\n", downdist, event)
      # Example of regex matching to find the down and distance
      # 1st and 10 at KC25
      if (m = downdist.match(/(^.*) and (\d+) at ([A-Z]+\d+)/i))!=nil
        @down, @dist, loc = m.captures
        m = @down.match(/(\d)/)
        @down = m.captures[0].to_i
        processLocation(loc,roadteamini)
      elsif (m = downdist.match(/(^.*) and ([A-Za-z]+) at ([A-Z]+\d+)/i))!=nil
        @down, @dist, loc = m.captures
        m = loc.match(/([A-Z]+)(\d+)/)
        @dist = m.captures[1].to_i
        #@dist = -1
        m = @down.match(/(\d)/)
        @down = m.captures[0].to_i
        processLocation(loc,roadteamini)
      end
      processEvent(event,downdist)
      @playnum += 1
    end
  end
end
end
def processEvent(event,downdist)
  if event.length >= 255
    event = event[0, 254]
  end
  @play = nil
  success = 0
  if (event =~ /1st down/i)
    success = 1
  end
  # Kill event to avoid creating play if
  # yardline is used instead of XX11 location format,
  # location format is messed up
  # there is a penalty
  # the data insists there is a 6th quarter
  if (event =~ /yardline/i || event =~ /penalty/i || event =~ /_/i ||
      event =~ /~/i || @yardline > 99 || @yardline < 1 || @quarter == 6) 
    event = nil
  # Events that are significant but are not needed as plays in our database (I think?)
  elsif (event =~ /rush attempt/i || event =~ /kick attempt/i || event =~ /start/i)
    if (m = event.match(/.* rush attempt good./i)) != nil
      @drivepoints += 2
    elsif (m = event.match(/.* kick attempt good./i)) != nil
      @drivepoints += 1
    elsif (m = event.match(/Start of (\d).* quarter/i)) != nil
      @quarter += 1
    elsif (m = event.match(/(.*) drive start at (.*)./i)) != nil
     # @driveteam= m.captures[0].upcase
      dteam, time = m.captures
      m = time.match(/(\d+:\d+)/i) 
      time = m.captures[0]
      dteam = dteam.upcase
      if dteam == @home.school
        @driveteam= @home
      else
        @driveteam = @road
      end
      # For special case of negative drive points from return touchdown.
      # Due to implementation, team that scored gets 0 points on their drive
      # even if they get the bonus 1 or 2 points after touchdown,
      # but that kind of drive is not relevation to our data so it doesn't matter
    # if @drivepoints > 0
     #   puts @yardline
      #  puts @drivepoints
       # @drive.update(points: @drivepoints)
     # end
      @drivepoints = 0
      @drive = Drive.find_or_create_by(game: @game, team: @driveteam, quarter: @quarter, starttime: time, location: @yardline, points: @drivepoints)
   # else
    #  puts "UNHANDLED MISC EVENT"
    #  puts event
    end
  
  # "Go for it" events 
  elsif (event =~ /pass/i || event =~ /sacked/i || event =~ /rush/i)

    if (m = event.match(/.* pass complete to .* for (\d+) yards? .* TOUCHDOWN/i)) != nil
      yards = m.captures[0].to_i
      @drivepoints += 6
      success = 1
    elsif event =~ /intercepted/i && event =~ /touchdown/i
      @drivepoints -= 6
      yards = 0
    elsif (m = event.match(/.* rush for (\d+) yards? .* TOUCHDOWN/i)) != nil
      yards = m.captures[0].to_i
      @drivepoints += 6
      success = 1
    elsif (m = event.match(/.* sacked .* (\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1]
    elsif (m = event.match(/.* sacked .* (\d+) yards? to the (\d+[A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1] 
    elsif (m = event.match(/.*intercepted.* return (\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1] 
    elsif (m = event.match(/.*intercepted.* return (\d+) yards? to the (\d+[A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1]
    elsif (m = event.match(/.*rush.*(\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards, location = m.captures[0].to_i, m.captures[1]
    elsif (m = event.match(/.*rush.*(\d+) yards? to the (\d+[A-Z]+\d+).*/i)) != nil
      yards, location = m.captures[0].to_i, m.captures[1]
    elsif (m = event.match(/.* pass complete to .* for loss of (\d+) yards?.*/i)) != nil
      yards = -m.captures[0].to_i
    elsif (m = event.match(/.* rush for loss of (\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1]
    elsif (m = event.match(/.* rush for loss of (\d+) yards? to the (\d+[A-Z]+\d+).*/i)) != nil
      yards = -m.captures[0].to_i
      location = m.captures[1]
    elsif (m = event.match(/.* pass complete to .* for (\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards, location = m.captures[0].to_i, m.captures[1]
    elsif (m = event.match(/.* pass complete to .* for (\d+) yards? to the (\d+[A-Z]+\d+).*/i)) != nil
      yards, location = m.captures[0].to_i, m.captures[1]
    elsif event =~ /pass incomplete./i
      yards = 0
    elsif event =~ /pass attempt/i && event =~ /failed/i
      yards = 0
    elsif event =~ /pass attempt/i && event =~ /good/i && event !=~ /\d/
      event = nil
      yards = 0
    elsif event =~ /no gain/ || event =~ /out-?of-?bounds/
      yards = 0
    #else
    #  puts "UNHANDLED RUSH EVENT"
    #  puts event
    end
    if event != nil
      # printf ("go. playnum, down, dist, yardline, quarter: "+(@playnum.to_s)+" "+(@down.to_s)+" "+(@dist.to_s)+" "+(@yardline.to_s)+" "+(@quarter.to_s)+"\n")
      @play = Play.find_or_create_by(drive: @drive, playnum: @playnum, down: @down, distance: @dist, location: @yardline, quarter: @quarter, description: event, result: yards)
      if @play != nil && @down == 4
        if yards == nil
        end
        Goforit.find_or_create_by(play: @play, success: success)
      end
    end
    
  # Punt events
  elsif (event =~ /punt/i)
    if (m = event.match(/.* punt (\d+) yards? .* return (\d+) yards? .*TOUCHDOWN.*/i)) != nil
      yards, ret = m.captures[0].to_i, m.captures[1].to_i
      net = yards - ret
      @drivepoints -= 6
      #@drive.update(points: @drivepoints)
    elsif (m = event.match(/.* punt (\d+) yards? .* return -(\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards, ret = m.captures[0].to_i, m.captures[1].to_i
      net = yards - ret
      location = m.captures[2]
    elsif (m = event.match(/.* punt (\d+) yards? .* return (\d+) yards? to the ([A-Z]+\d+).*/i)) != nil
      yards, ret = m.captures[0].to_i, m.captures[1].to_i
      net = yards - ret
      location = m.captures[2]
    elsif (m = event.match(/.* punt (\d+) yards? to the ([A-Z]+\d+).*fair catch.*/i)) != nil
      yards, location = m.captures[0].to_i, m.captures[1]
      net = yards
    elsif (m = event.match(/.* punt (\d+) yards? to the ([A-Z]+\d+).*downed.*/i))!=nil
      yards, location = m.captures[0].to_i, m.captures[1]
      net = yards
    elsif (event =~ /out-?of-?bounds/i || event =~ /touchback/i ||
        event =~ /blocked/i)
      yards = 0
      net = 0
    #else
    #  puts "UNHANDLED PUNT EVENT"
    #  puts event
    end
    if event != nil
      #printf ("punt. playnum, down, dist, yardline, quarter: "+(@playnum.to_s)+" "+(@down.to_s)+" "+(@dist.to_s)+" "+(@yardline.to_s)+" "+(@quarter.to_s)+"\n")
      @play = Play.find_or_create_by(drive: @drive, playnum: @playnum, down: @down, distance: @dist, location: @yardline, quarter: @quarter, description: event, result: net)
      if @play != nil
        Punt.find_or_create_by(play: @play, distance: yards, net: net)
      end
    end
  
  # field goal events
  elsif (event =~ /field goal/i)
    if (event =~ /attempt/i && event =~ /good/i) 
	success = 1
        @drivepoints += 3
    elsif (event =~ /fail/i || event =~ /miss/i || event =~ /blocked/i || event =~ /fumble/i || event =~ /botched/i || event =~ /bad/i)
        success = 0
    end
    if(m = event.match(/.* field goal attempt from (\d+).*/i))!=nil
       result = m.captures[0].to_i
    end
    if event != nil
      #printf ("fg. playnum, down, dist, yardline, quarter: "+(@playnum.to_s)+" "+(@down.to_s)+" "+(@dist.to_s)+" "+(@yardline.to_s)+" "+(@quarter.to_s)+"\n")
      @play = Play.find_or_create_by(drive: @drive, playnum: @playnum, down: @down, distance: @dist, location: @yardline, quarter: @quarter, description: event, result: result)
      if @play != nil
	 FieldGoal.find_or_create_by(play: @play, distance: @yardline, success: success)
      end
    end
  end   
=begin 
  else
    if (event =~ /time ?out/i || event =~ /penalty/i || event =~ /kick ?off/i ||
            event =~ /end of/i || event =~ /toss/i || event =~ /ball/i || event =~ /hurry/i || 
            event =~ /\d\w+ and \d+/i || event =~ /^clock/i || event =~ /QB/i ||
            event =~ /ejected/i || event =~ /helmet/i || event =~ /receiv/i ||
            event =~ /rain/i || event =~ /delay/i || event =~ /injured/i ||
B
            event =~ /coach/i || event =~ /team/i || event =~ /win/i || event =~ /capt/i ||
A
            event =~ /warning/i || event =~ /left/i || event =~ /right/i ||
            event =~ /unsportsman/i || event =~ /stadium/i || event =~ /coin/i ||
            event =~ /second/i || event =~ /foul/i || event =~ /disqualified/i ||
            event =~ /won/i || event =~ /game/i || event =~ /allowed/i || event =~ /carries/i ||
            event =~ /pat kick/i || event =~ /quarterback/i || event =~ /caught/i ||
            event =~ /middle/i || event =~ /official/i || event =~ /play/i ||
            event =~ /intentional/i || event =~ /wearing/i || event =~ /defend/i ||
            event =~ /\(.*\)/i)
      #ignore
    else
      puts "UNHANDLED CASE\n#{event}"
    end
=end
  if @drivepoints > 0
     @drive.update(points: @drivepoints)
  end
  #   @drivepoints = 0
   #  @drive = Drive.find_or_create_by(game: @game, team: @driveteam, quarter: @quarter, starttime: time, location: @yardline, points: @drivepoints)
 end

  
def processLocation(location,roadteamini)
  m = location.match(/([A-Z]+)(\d+)/)
  num = m.captures[1].to_i
  teamini = m.captures[0]
  ishome = 1
  isroad = 0
  if @driveteam.school == @home.school
    ishome = 1
    isroad = 0
  elsif @driveteam.school == @road.school
    ishome = 0
    isroad = 1
  end
  #puts @driveteam.school
  #puts @home.school
  #puts @road.school
  #puts '- ------ -- --'
  if ishome==1
     if teamini !=roadteamini
	@yardline = num
     elsif teamini == roadteamini
	@yardline = 100-num
     end 
  elsif isroad ==1
     if teamini != roadteamini
        @yardline = 100-num
     elsif teamini == roadteamini
        @yardline = num
     end
  end
end 

def main
  root='play-by-play'
  Dir[root+'/2016/*'].each do |team|
  end
    ['2016', '2015', '2014', '2013', '2012', '2011'].each do |year|
    Dir[root+'/'+year+'/*'].each do |team|
      m = team.match(/.*\/.*\/(\w).*/)
      letter = m.captures[0]
      Dir[team+'/*'].each do |file|
        puts file
        processPlayByPlay(file)
      end
    end
  end
end
  
if __FILE__ == $0
  # test on only ony play-by-play record at a @time
 # oalidates :distance, presence: true, numericality: { only_integer: true }
  # comment this out when you want to process records in bulk
 # processPlayByPlay('play-by-play/2016/Lycoming/20161008_bl7v.xml')
 # processPlayByPlay('play-by-play/2016/Lycoming/20160903_nbg8.xml')
  # Use main to run through all data that we have
  main

end
