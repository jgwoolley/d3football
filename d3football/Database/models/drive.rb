require_relative 'application_record'

class Drive < ApplicationRecord
  belongs_to :game
  belongs_to :team
  has_many :play
  validates :game_id, presence: true
  validates :team_id, presence: true
  validates :location, presence: true, numericality: { only_integer: true, greater_than: 0, less_than: 100 }
  validates :quarter, presence: true
  validates :starttime, presence: true
  validates :points, presence: true, numericality: { only_integer: true }
end
