require_relative 'config/boot'
require_relative 'models/team'
require_relative 'models/game'
require_relative 'models/drive'
require_relative 'models/play'
require_relative 'models/punt'
require_relative 'models/goforit'
require_relative 'models/field_goal'


# Look up by name of the school, create if it doesn't exist
home = Team.find_or_create_by(school: 'Knox')
road = Team.find_or_create_by(school: 'Monmouth')

# gamecode would be brh4 of the following URL:
# http://d3football.com/seasons/2016/boxscores/20161008_brh4.xml
# this seems like a unique way to identify each game

# first, try to look up by the gamecode, which is unique for each game (I think?)
game = Game.find_by(gamecode: 'wxyz')
if game == nil
  # if we can't find the record, then create it!
  game = Game.find_or_create_by(home_id: home.id, road_id: road.id, home_score: 28, road_score: 27, date: '2016-11-03', gamecode: 'wxyz')
end

if game.invalid?
  # check for validation errors
  # see models/game.rb for the validations
  p game.errors.messages
  exit
end

drive = Drive.find_by(game: game, team: home, quarter: 1, starttime: '15:00', location: 20)
if drive == nil
  # again, create if we can't find it
  # we can't search by points because we don't know how many points a drive scores
  # when we first encounter a drive. So if we find_or_create_by and set points=0
  # we will create extra records that we don't want or need
  drive = Drive.find_or_create_by(game: game, team: home, quarter: 1, starttime: '15:00', location: 20, points: 0)
end
if drive.invalid?
  p drive.errors.messages
  exit
end
# I am not validating the individual plays because the information is pretty
play1 = Play.find_or_create_by(drive: drive, playnum: 1, down: 1, distance: 10, quarter: 1, location: 20, result: 5, description: "Spacco passed to Dooley for 5 yards (Bunde)")
play2 = Play.find_or_create_by(drive: drive, playnum: 2, down: 2, distance: 5, quarter: 1, location: 25, result: 2, description: "Dooley passes to Spacco for 2 yards (Bunde)")
play3 = Play.find_or_create_by(drive: drive, playnum: 3, down: 3, distance: 3, quarter: 1, location: 27, result: 1, description: "Spacco runs left for 1 yard (Bunde)")
play4 = Play.find_or_create_by(drive: drive, playnum: 4, down: 4, distance: 2, quarter: 1, location: 28, result: 0, description: "Spacco incomplete pass")
goforit = Goforit.find_or_create_by(play: play4, success: 0)
# Once we get through all of the plays, we should update the drive with the number of points that were scored
drive.update(points: 0)


drive = Drive.find_by(game: game, team: home, quarter: 1, starttime: '14:00', location: 70)
if drive == nil
  drive = Drive.find_or_create_by(game: game, team: home, quarter: 1, starttime: '14:00', location: 70, points: 0)
end
play1 = Play.find_or_create_by(drive: drive, playnum: 1, down: 1, distance: 10, quarter: 1, location: 70, result: 5, description: "Spacco passed to Dooley for 5 yards (Smith)")
play2 = Play.find_or_create_by(drive: drive, playnum: 2, down: 2, distance: 5, quarter: 1, location: 75, result: -5, description: "Spacco runs for -5 yards (Jones)")
play3 = Play.find_or_create_by(drive: drive, playnum: 3, down: 3, distance: 3, quarter: 1, location: 70, result: 8, description: "Dooley runs right for 8 yard (Melville)")
play4 = Play.find_or_create_by(drive: drive, playnum: 4, down: 4, distance: 2, quarter: 1, location: 78, result: 0, description: "Bunde makes 39 yard field goal")
fieldgoal = FieldGoal.find_or_create_by(play: play4, success: 1, distance: 39)
# update the drive with the number of points scored
drive.update(points: 3)

drive = Drive.find_by(game: game, team: home, quarter: 1, starttime: '10:00', location: 10)
if drive == nil
  drive = Drive.find_or_create_by(game: game, team: home, quarter: 1, starttime: '10:00', location: 10, points: 0)
end
play1 = Play.find_or_create_by(drive: drive, playnum: 1, down: 1, distance: 10, quarter: 1, location: 10, result: 5, description: "Spacco passed to Dooley for 5 yards (Smith)")
play2 = Play.find_or_create_by(drive: drive, playnum: 2, down: 2, distance: 5, quarter: 1, location: 15, result: -5, description: "Spacco runs for -5 yards (Jones)")
play3 = Play.find_or_create_by(drive: drive, playnum: 3, down: 3, distance: 3, quarter: 1, location: 10, result: 8, description: "Dooley runs right for 8 yard (Melville)")
play4 = Play.find_or_create_by(drive: drive, playnum: 4, down: 4, distance: 2, quarter: 1, location: 18, result: 0, description: "Bunde punts 50 yards, 10 yard return")
punt = Punt.find_or_create_by(play: play4, distance: 50, net: 40)
drive.update(points: 0)
