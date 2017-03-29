require_relative 'application_record'

class Play < ApplicationRecord
  belongs_to :drive
  validates :playnum, presence: true, numericality: { only_integer: true }
  validates :down, presence: true, numericality: { only_integer: true, greater_than_or_equal_to: 1, less_than_or_equal_to: 4 }
  validates :distance, presence: true, numericality: { only_integer: true }
  validates :quarter, presence: true
  validates :location, presence: true, numericality: { only_integer: true, great_than: 0, less_than: 100 }
  validates :description, presence: true
  validates :result, presence: true, numericality: { only_integer: true }
end
