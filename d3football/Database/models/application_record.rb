# This is a standard class for Active Record with Ruby
# It basically creates an extra level of indirection
# I don't think this assignment will get into any of these details
class ApplicationRecord < ActiveRecord::Base
  self.abstract_class = true
end
