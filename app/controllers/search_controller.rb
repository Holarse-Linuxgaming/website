class SearchController < ApplicationController

  def index
    @results = PerformSearch.(params[:q])
  end

end
