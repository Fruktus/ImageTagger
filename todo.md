not sure how exactly the structure worked but go about it like this:
create new interface with methods that are required by controller,
like get_mode, display_image_from_path, etc.
make the view class which will be part of the form and will implement the interface,
make the controller use any class with that interface

that will allow me to easily switch to javafx

later on go about refactoring the controller itself, shouldnt be that hard.
for ex make interface for controller, since the form will have to update the keybindings or smth