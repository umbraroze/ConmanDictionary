#!/usr/bin/ruby
# Used to clean up old-style adhoc definitions to new-style word classes.

require 'rexml/document'

dictionary = REXML::Document.new(File.open(ARGV[0]))

dictionary.elements.each('//entry') do |e|
  t = e.elements['definition'].text
  if(t =~ /^(n|a|v|pr|p)\.\s+/)
    e.attributes['class'] = case $1
                            when 'n' then 'Noun'
                            when 'a' then 'Adjective'
                            when 'v' then 'Verb'
                            when 'pr' then 'Pronoun'
                            when 'p' then 'Particle'
                            end
    t.gsub!(/^(n|a|v|pr|p)\.\s+/,'')
    e.elements['definition'].text = t
  end
end
puts dictionary
