class NewsDecorator < NodeDecorator

  def secondary
    object.subtitle
  end

  def tags
    news_list
  end

end
