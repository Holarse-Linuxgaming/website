class RenderContent

  def self.call(obj_with_content)
    raise "No content" unless obj_with_content.respond_to? :content

    Kramdown::Document.new(obj_with_content.content).to_html.html_safe
  end

end