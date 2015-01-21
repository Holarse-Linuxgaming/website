# -*- coding: utf-8 -*-
class SessionController < ApplicationController
  

  def new
    add_breadcrumb "Login", :login_path
    render :login
  end
  
  def login
    begin
      user = UserLogin.call(params)
      session[:user_id] = user.id
      redirect_to root_path, success: "Willkommen zurück #{user.username}"
    rescue Exception => e
      Rails.logger.debug(e)
      flash[:danger] = e.message
      render :login
    end
  end

  def logout
    add_breadcrumb "Logout", :logout_path

    if current_user
      flash[:info] = "Auf Wiedersehen #{current_user.username}!"
      session[:user_id] = nil      
    end
    redirect_to root_path
  end
  
  private
  
  def login_params
    params.require(:login).permit(:username, :password)
  end
  
end
