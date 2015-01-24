class News < ActiveRecord::Base
  extend FriendlyId

  validates :title, :content, presence: true

  has_many :comments, as: :commentable

end
