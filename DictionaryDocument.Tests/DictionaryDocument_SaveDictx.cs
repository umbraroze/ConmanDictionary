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
        // TODO: Generate mock document, then just try to save it somewhere
        Assert.Pass();
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