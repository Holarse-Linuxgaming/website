class RenameActiveRecordFields < ActiveRecord::Migration
  def change
    rename_column :articles, :frozen, :unchangable
    rename_column :news, :frozen, :unchangable
  end
end
