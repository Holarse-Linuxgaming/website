class ArticleDecorator < Draper::Decorator
  delegate_all

  # Define presentation-specific methods here. Helpers are accessed through
  # `helpers` (aka `h`). You can override attributes, for example:
  #
  #   def created_at
  #     helpers.content_tag :span, class: 'time' do
  #       object.created_at.strftime("%a %m/%d/%y")
  #     end
  #   end

  def rendered_content
    RenderContent.(object)
  end

  def secondary
    object.alternatives
  end

  def last_update
    object.updated_at || object.created_at
  end

  def last_author
    object.try(:user).try(:username)
  end

end
