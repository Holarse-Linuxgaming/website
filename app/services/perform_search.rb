class PerformSearch

  def self.call(query)
    Article.where("title like ?", "%#{query}%")
  end

end
