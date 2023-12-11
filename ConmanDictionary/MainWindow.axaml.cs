using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using ReactiveUI;
using System.Reactive;
using System.ComponentModel;
using System.Diagnostics;

namespace ConmanDictionary
{

    public class MainWindowViewModel : INotifyPropertyChanged
    {
        string buttonText = "Click Me!";

        private Window? _parent;

        public MainWindowViewModel(Window parent) : base()
        {
            _parent = parent;
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

        public async void ShowAboutWindowCommand()
        {
            System.Diagnostics.Debug.WriteLine("Show About Window");
            AboutWindow about = new AboutWindow();
            await about.ShowDialog(_parent);
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
