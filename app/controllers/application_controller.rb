class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  private
  
  def current_user
    session[:user_id] ? User.find(session[:user_id]) : GuestUser.new
  end
  
  helper_method :current_user
  
end
