class News < ActiveRecord::Base
  extend FriendlyId

  friendly_id :title, use: :slugged

  validates :title, :content, presence: true

  has_many :comments, as: :commentable

end
