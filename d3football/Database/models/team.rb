require_relative 'application_record'

class Team < ApplicationRecord
  validates :school, presence: true
end
