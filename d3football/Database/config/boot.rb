# Setup the listed gems in the gemfile
require 'bundler/setup'

# Actually require the gems
Bundler.require

# Setup ActiveRecord
require 'logger'
ActiveRecord::Base.configurations = YAML.load_file('config/database.yml')
ActiveRecord::Base.establish_connection(:default)
ActiveRecord::Base.logger = Logger.new(STDOUT)
