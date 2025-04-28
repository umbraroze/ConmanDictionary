namespace DictionaryDocument.Tests;

[TestFixture]
public class SaveDictx
{
    [SetUp]
    public void Setup()
    {
    }

    [Test]
    public void SaveGeneratedDocument()
    {
        // Generate mock document.
        Dictionary mockDocument = Generators.GetMockDocument();
        // Save it to disk.
        FileInfo mockDocFile = new FileInfo(Path.GetTempFileName());
        mockDocument.SaveDictx(mockDocFile);
        // Should throw hella exceptions above, so we just check if the file exists
        Assert.That(File.Exists(mockDocFile.FullName),$"File wasn't successfully saved.");
    }

    [Test]
    public void TestRoundtrip()
    {
        // Generate mock document.
        Dictionary mockDocument = Generators.GetMockDocument();
        // Save it to disk.
        FileInfo mockDocFile = new FileInfo(Path.GetTempFileName());
        mockDocument.SaveDictx(mockDocFile);

        // Load it back up from disk.
        Dictionary loadedMockDocument = Dictionary.LoadDictx(mockDocFile);
        Assert.That(mockDocument, Is.EqualTo(loadedMockDocument), $"The document wasn't the same after it was loaded from disk.");
    }
}