class SessionController < ApplicationController
  
  def new
    render :login
  end
  
  def login
    begin
      user = UserLogin.call(params[:login][:username], params[:login][:password])
      session[:user_id] = user.id
      redirect_to root_path, notice: "Willkommen zurück #{user.username}"
    rescue Exception => e
      Rails.logger.debug(e)
      flash[:alert] = e.message
      render :login
    end
  end

  def logout
    if current_user
      flash[:notice] = "Auf Wiedersehen #{current_user.username}!"
      session[:user_id] = nil      
    end
    redirect_to root_path
  end
  
  private
  
  def login_params
    params.require(:login).permit(:username, :password)
  end
  
end
