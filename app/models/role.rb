class Role < ActiveRecord::Base

  # relation
  has_and_belongs_to_many :users  

  # validation
  validates :name, :label, presence: true, uniqueness: true
  
end
