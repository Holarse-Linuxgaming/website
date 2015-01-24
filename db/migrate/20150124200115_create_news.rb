class CreateNews < ActiveRecord::Migration
  def change
    create_table :news do |t|
      t.string :title, null: false
      t.string :subtitle

      t.text :content

      t.boolean :frozen, default: false
      t.boolean :commentsallowed, default: true

      t.string :slug

      t.timestamps null: false
    end
  end
end
