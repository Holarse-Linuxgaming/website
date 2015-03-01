class Article < ActiveRecord::Base
  extend FriendlyId

  friendly_id :title, use: :slugged

  validates :title, :content, presence: true
  validates :title, uniqueness: true

  has_many :comments, as: :commentable
  belongs_to :user

end
