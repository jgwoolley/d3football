require 'yaml'
map = YAML.load_file("teammap.yml")

# check if a map has a key
if map.has_key?('Knox College')
  # lookup the value, and print it
  code = map['Knox College']
  puts "Knox College => #{code}"
end

# iterate through each key-value pair
map.each do |k,v|
  puts "#{k} => #{v}"
end
