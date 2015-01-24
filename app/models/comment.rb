class Comment < ActiveRecord::Base

  belongs_to :commentable, polymorphic: true

  validates :user, :content, presence: true

end
