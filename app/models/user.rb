class User < ActiveRecord::Base
  has_secure_password
  
  validates :username, :email, presence: true
  validates :username, :email, uniqueness: true
  
  def is_legacy?
    self.legacy_password.present?
  end
end
