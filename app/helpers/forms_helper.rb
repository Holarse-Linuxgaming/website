#encoding: utf-8
module FormsHelper

  def display_form_errors(model)
    return if model.errors.empty?
    content_tag :div, class: "alert alert-danger fade in" do
      concat content_tag :h4, "Im Formular sind fehlerhafte Einträge vorhanden"
      model.errors.full_messages.each do |error|
        concat content_tag :p, error
      end
    end
  end

end
