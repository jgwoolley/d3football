require_relative 'application_record'

class Game < ApplicationRecord
  belongs_to :team, foreign_key: "home_id"
  belongs_to :team, foreign_key: "road_id"

  validates :home_id, presence: true
  validates :road_id, presence: true
  validates :home_score, presence: true, numericality: { only_integer: true }
  validates :road_score, presence: true, numericality: { only_integer: true }
  validates :date, presence: true
  validates :gamecode, presence: true
end
