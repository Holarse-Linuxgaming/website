class User < ActiveRecord::Base
  has_secure_password
  
  # relation
  has_and_belongs_to_many :roles
  
  #validation
  validates :username, :email, presence: true, uniqueness: true
end
