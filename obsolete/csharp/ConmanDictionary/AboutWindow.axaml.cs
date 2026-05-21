using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using System.ComponentModel;

namespace ConmanDictionary
{
    public class AboutWindowViewModel : INotifyPropertyChanged
    {
        private Window? _parent;

        public AboutWindowViewModel(Window parent) : base()
        {
            _parent = parent;
        }

        public void AboutWindowClose()
        {
            _parent?.Hide();
        }

        public event PropertyChangedEventHandler? PropertyChanged;
    }

    public partial class AboutWindow : Window
    {

        public AboutWindow()
        {
            InitializeComponent();
            DataContext = new AboutWindowViewModel(this);
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
