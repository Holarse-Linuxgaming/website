class CreateArticles < ActiveRecord::Migration
  def change
    create_table :articles do |t|
      t.string :title, null: false
      t.string :alternatives

      t.text :content, null: false

      t.boolean :frozen, default: false
      t.boolean :commentsallowed, default: true

      t.string :slug

      t.timestamps null: false
    end
  end
end
