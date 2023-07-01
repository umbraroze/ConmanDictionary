using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using ReactiveUI;
using System.Reactive;
using System.ComponentModel;

namespace ConmanDictionary
{

    public class MainWindowViewModel : INotifyPropertyChanged
    {
        string buttonText = "Click Me!";

        private Window? _parent;
        private AboutWindow? _aboutWindow;

        public MainWindowViewModel(Window parent) : base()
        {
            this._parent = parent;
        }

        public string ButtonText
        {
            get => buttonText;
            set
            {
                buttonText = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(nameof(ButtonText)));
            }
        }

        public event PropertyChangedEventHandler? PropertyChanged;

        public void ButtonClicked() => ButtonText = "Hello, Avalonia!";

        public void QuitCommand()
        {
            System.Diagnostics.Debug.WriteLine("Quit");
            _parent?.Close();
        }

        public void ShowAboutWindowCommand()
        {
            System.Diagnostics.Debug.WriteLine("Show About Window");
            if (_aboutWindow == null)
            {
                _aboutWindow = new AboutWindow();
                _aboutWindow.Show();
            }
        }
    }

    public partial class MainWindow : Window
    {

        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowViewModel(this);
#if DEBUG
            this.AttachDevTools();
#endif
        }


        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }

    }
}
