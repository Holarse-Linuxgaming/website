class SearchController < ApplicationController

  add_breadcrumb "Suchergebnisse"

  def index
    @results = PerformSearch.(params[:q]).decorate
    add_breadcrumb "#{@results.size} Treffer zu #{params[:q]}"
  end

end
