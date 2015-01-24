class CreateComments < ActiveRecord::Migration
  def change
    create_table :comments do |t|
      t.references :user
      t.references :commentable, polymorphic: true, index: true
      t.text :content
      t.boolean :enabled, default: true

      t.timestamps null: false
    end
  end
end
