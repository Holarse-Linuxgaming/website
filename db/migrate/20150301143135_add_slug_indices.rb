class AddSlugIndices < ActiveRecord::Migration
  def change
    add_index :articles, :slug, unique: true
    add_index :news, :slug, unique: true
  end
end
