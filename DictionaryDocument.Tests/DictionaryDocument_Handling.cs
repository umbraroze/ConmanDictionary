namespace DictionaryDocument.Tests;

[TestFixture]
public class Handling
{
    [SetUp]
    public void Setup()
    {
    }

    [Test]
    public void MockDocumentsEqual()
    {
        Dictionary mockDocument1 = Generators.GetMockDocument();
        Dictionary mockDocument2 = Generators.GetMockDocument();
        
        Assert.That(mockDocument1, Is.EqualTo(mockDocument2), $"Two freshly generated mock documents aren't the same.");
    }
}