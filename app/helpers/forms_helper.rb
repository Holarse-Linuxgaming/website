module FormsHelper

  def display_form_errors(errors)
    content_tag :div do
      errors.each do |error|
        concat content_tag :p, error
      end
    end
  end

end
