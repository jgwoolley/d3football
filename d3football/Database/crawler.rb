#
# Crawl the d3football listing of D3 teams here:
# http://www.d3football.com/teams/index
# and finds links to each team's page.
# Then, crawl each of those pages to find
# the team's seasons from 2011 to 2016,
# find the play-by-play logs, and download them
# into folders.
#

require 'rubygems'
require 'nokogiri'
require 'open-uri'
require 'fileutils'
require 'set'

#require_relative 'parser'


#
# Crawls the list of all teams, and returns a hash mapping
# team name (i.e. 'Knox') to the URL of the page containing
# links to all of their seasons, such as this one:
# http://d3football.com/teams/Knox/2016/index
#
def crawlTeamLinks(rooturl, path)
  page = Nokogiri::HTML(open(rooturl+path))

  teamlinks = Hash.new
  page.css('tr.roster-row0 td:first-of-type a, tr.roster-row1 td:first-of-type a').each do |e|
    team = e.text
    link = e['href']
    teamlinks[team]=rooturl+link.gsub('info/..', '2016')
  end
  return teamlinks
end

#
# Crawls a page containing links to all of the previous seasons.
# For the 2011-2016 seasons, find the links to each of the play-by-play
# charts (which all have the text "BX", for "box score", even though it's
# the play-by-play and not the box score), and download and save the file
# into a play-by-play/year/team/pbpfile.xml
#
def crawlTeam(teamname, url, rooturl, years)
  years.each do |year|
    puts 'year is '+year
    # if play-by-play/year/teamname already exists then skip it
    if File.directory?("play-by-play/%s/%s" % [year, teamname.gsub(' ', '_')])
      printf("skipping %s because play-by-play/%s/%s exists\n" % [teamname.gsub(' ', '_'), year, teamname])
      next
    end
    yearurl=url.gsub('2016', year)

    # done is a set that tracks whether we've already found a link.
    # this is necessary because the web pages for each team list both all games
    # and conference games, which are a subset of all games, which means
    # some games are listed twice. So we skip anything we've already seen.
    # (next in Ruby is like continue in Java or Python)
    done=Set.new
    begin
      page = Nokogiri::HTML(open(yearurl))
    rescue
      printf("Unable to open %s\n" % [yearurl])
      next
    end
    page.css('a:contains("BX")').each do |e|
      link = rooturl + e['href']
      if done.include?(link)
        next
      end
      done.add(link)

      # creat ethe link and use the full
      pbp=link+'?view=plays&dec=printer-decorator'
      dir='play-by-play/'+year+'/'+teamname.gsub(' ', '_')
      filename=link[link.rindex('/')+1..-1]
      fullpath=dir+'/'+filename

      #printf("%s %s %s %s\n", link, pbp, filename, dir)
      printf("%s\n", pbp)

      # create the local folder, if it doesn't already exist
      if ! File.exists?(File.expand_path(dir))
        FileUtils::mkdir_p(dir)
      end
      # copy the play-by-play (pbp) file from its location on the web
      # to our local folder.
      download = open(pbp)
      IO.copy_stream(download, fullpath)
    end
  end
end

def test1
  # function for testing the crawlTeam() function on only one sample team.
  # if you don't start by testing this function in isolation, there's basically
  # no chance that the whole program will work.
  crawlTeam('Knox', 'http://d3football.com/teams/Knox/2016/index', 'http://d3football.com', ['2015', '2016'])
end

# __FILE__ == $0 is a Ruby construct that asks us if this is the main Ruby file
# that is being executed. This is equivalent to __name__ == '__main__' in Python.
# This is necessary because code outside of a function or an if in Ruby will be
# executed anytime the file is executed, as well as whenever the file is
# imported (require)
if __FILE__ == $0
  rooturl = 'http://d3football.com'
  path = '/teams/index';
  years=['2016', '2015', '2014', '2013', '2012', '2011']
  # get the links to the pages containing the play by plays for each team
  teamlinks = crawlTeamLinks(rooturl, path)
  teamlinks.each do |team, link|
    printf("%s => %s\n", team, link)
    # now find all the play by plays and save them to a file
    # check that the team does not exist in the folder
    crawlTeam(team, link, 'http://d3football.com', years)
  end
end
