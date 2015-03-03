class ArticleDecorator < NodeDecorator

  def secondary
    object.alternatives
  end

  def tags
    genre_list + common_list
  end

end
