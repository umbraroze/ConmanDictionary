﻿//------------------------------------------------------------------------------
// <auto-generated>
//     Työkalu muodosti tämän koodin.
//     Suorituksenaikainen versio:4.0.30319.42000
//
//     Tähän tiedostoon tehdyt muutokset saattavat aiheuttaa virheellistä toimintaa. Ne menetetään, jos
//     koodi muodostetaan uudelleen.
// </auto-generated>
//------------------------------------------------------------------------------

using System.Xml.Serialization;

// 
// This source code was auto-generated by xsd, Version=4.6.1055.0.
// 


/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true)]
[System.Xml.Serialization.XmlRootAttribute(Namespace="", IsNullable=false)]
public partial class dictionarydatabase {
    
    private string notepadField;
    
    private string[] todoitemsField;
    
    private @class[] wordclassesField;
    
    private category[] categoriesField;
    
    private dictionarydatabaseDefinitions[] definitionsField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    public string notepad {
        get {
            return this.notepadField;
        }
        set {
            this.notepadField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    [System.Xml.Serialization.XmlArrayItemAttribute("todoitem", Form=System.Xml.Schema.XmlSchemaForm.Unqualified, IsNullable=false)]
    public string[] todoitems {
        get {
            return this.todoitemsField;
        }
        set {
            this.todoitemsField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    [System.Xml.Serialization.XmlArrayItemAttribute("class", IsNullable=false)]
    public @class[] wordclasses {
        get {
            return this.wordclassesField;
        }
        set {
            this.wordclassesField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlArrayAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    [System.Xml.Serialization.XmlArrayItemAttribute("category", IsNullable=false)]
    public category[] categories {
        get {
            return this.categoriesField;
        }
        set {
            this.categoriesField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute("definitions", Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    public dictionarydatabaseDefinitions[] definitions {
        get {
            return this.definitionsField;
        }
        set {
            this.definitionsField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true)]
[System.Xml.Serialization.XmlRootAttribute(Namespace="", IsNullable=false)]
public partial class @class {
    
    private string abbreviationField;
    
    private string nameField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute()]
    public string abbreviation {
        get {
            return this.abbreviationField;
        }
        set {
            this.abbreviationField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute(DataType="ID")]
    public string name {
        get {
            return this.nameField;
        }
        set {
            this.nameField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true)]
[System.Xml.Serialization.XmlRootAttribute(Namespace="", IsNullable=false)]
public partial class category {
    
    private string nameField;
    
    private bool flaggedField;
    
    public category() {
        this.flaggedField = false;
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute(DataType="ID")]
    public string name {
        get {
            return this.nameField;
        }
        set {
            this.nameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute()]
    [System.ComponentModel.DefaultValueAttribute(false)]
    public bool flagged {
        get {
            return this.flaggedField;
        }
        set {
            this.flaggedField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true)]
public partial class dictionarydatabaseDefinitions {
    
    private entry[] entryField;
    
    private string languageField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute("entry")]
    public entry[] entry {
        get {
            return this.entryField;
        }
        set {
            this.entryField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute()]
    public string language {
        get {
            return this.languageField;
        }
        set {
            this.languageField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(AnonymousType=true)]
[System.Xml.Serialization.XmlRootAttribute(Namespace="", IsNullable=false)]
public partial class entry {
    
    private string termField;
    
    private string definitionField;
    
    private bool flaggedField;
    
    private string classField;
    
    private string categoryField;
    
    public entry() {
        this.flaggedField = false;
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    public string term {
        get {
            return this.termField;
        }
        set {
            this.termField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
    public string definition {
        get {
            return this.definitionField;
        }
        set {
            this.definitionField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute()]
    [System.ComponentModel.DefaultValueAttribute(false)]
    public bool flagged {
        get {
            return this.flaggedField;
        }
        set {
            this.flaggedField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute(DataType="IDREF")]
    public string @class {
        get {
            return this.classField;
        }
        set {
            this.classField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAttributeAttribute(DataType="IDREF")]
    public string category {
        get {
            return this.categoryField;
        }
        set {
            this.categoryField = value;
        }
    }
}