class NodeDecorator < Draper::Decorator
  delegate_all

  def rendered_content
    RenderContent.(object)
  end

  def last_update
    object.updated_at || object.created_at
  end

  def last_author
    object.try(:user).try(:username)
  end

end
