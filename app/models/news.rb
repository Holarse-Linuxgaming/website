class News < ActiveRecord::Base
  extend FriendlyId

  acts_as_taggable_on :news

  friendly_id :title, use: :slugged

  validates :title, :content, presence: true

  has_many :comments, as: :commentable
  belongs_to :user

end
