require_relative 'application_record'

class FieldGoal < ApplicationRecord
  belongs_to :play
  self.primary_key = "play_id"
  
  validates :success, presence: true, numericality: { integer_only: true, greater_than_or_equal_to: 0, less_than_or_equal_to: 1 }
end
