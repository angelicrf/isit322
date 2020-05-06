using System.Collections.Generic;
using WithVisualStudio.Models;

namespace WithVisualStudio.Controllers
{
    public interface IRepository
    {
        IEnumerable<Product> Products { get; }
        void AddProduct(Product p);
    }
}