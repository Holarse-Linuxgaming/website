class AddUserToNewsAndArticle < ActiveRecord::Migration
  def change
    add_reference :articles, :user, index: true
    add_reference :news, :user, index: true
  end
end
