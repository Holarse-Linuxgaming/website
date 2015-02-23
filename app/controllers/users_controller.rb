class UsersController < ApplicationController
  
  add_breadcrumb "Benutzer"

  def new
    add_breadcrumb "Registrieren", register_path

    @user = User.new
  end
  
  def create
    @user = User.create(user_params)
    
    begin
      new_user = UserRegister.call(@user)
      flash[:success] = "Das Konto wurde erfolgreich angelegt. Du kannst dich jetzt als #{@user.username} einloggen"
      redirect_to root_path
    rescue Exception => e
      Rails.logger.debug("Exception beim User-Register: #{e}")
      flash[:danger] = e.message
      render :new
    end
  end
  
  def edit
  end

  def show
    add_breadcrumb current_user.username, current_user
    @user = User.friendly.find(current_user.id)
  end

  def update
  end

  def destroy
  end

  def index
  end
  
  private
  
  def user_params
    params.require(:user).permit(:username, :email, :password, :password_confirmation)
  end  
  
end
