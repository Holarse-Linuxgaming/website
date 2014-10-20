class User < ActiveRecord::Base
  has_secure_password
  
  validates :username, :email, presence: true 
  validates :username, :email, uniqueness: true
end
