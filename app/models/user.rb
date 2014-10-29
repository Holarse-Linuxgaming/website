class User < ActiveRecord::Base
  extend FriendlyId
  
  has_secure_password
  
  friendly_id :username, use: :slugged
  
  # relation
  has_and_belongs_to_many :roles
  
  #validation
  validates :username, :email, presence: true, uniqueness: true
  validates :password, presence: true, on: :create, confirmation: true
end
