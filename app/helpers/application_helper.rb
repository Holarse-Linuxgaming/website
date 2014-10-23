module ApplicationHelper
  
  def is_guest?
    current_user.class == GuestUser
  end
  
end
