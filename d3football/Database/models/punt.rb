require_relative 'application_record'

class Punt < ApplicationRecord
  belongs_to :play
  self.primary_key = "play_id"
  validates :distance, presence: true, numericality: { integer_only: true }
  validates :net, presence: true, numericality: { integer_only: true }
end
