class Article < ActiveRecord::Base
  extend FriendlyId

  acts_as_taggable_on :genres, :multiplayer, :common

  friendly_id :title, use: :slugged

  validates :title, :content, presence: true
  validates :title, uniqueness: true

  has_many :comments, as: :commentable
  belongs_to :user

end
