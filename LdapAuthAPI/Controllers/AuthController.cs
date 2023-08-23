using LdapAuthAPI.Models;
using Microsoft.AspNetCore.Mvc;
using System.DirectoryServices.AccountManagement;
namespace LdapAuthAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        [HttpPost("authenticate")]
        public IActionResult Authenticate([FromBody] AuthRequestModel request)
        {
            if (ModelState.IsValid)
            {
                try
                {
                    using (PrincipalContext context = new PrincipalContext(ContextType.Domain, "******.****.intra"))
                    {
                        bool auth = context.ValidateCredentials(request.username, request.password);
                        return auth ? Ok() : Unauthorized();
                    };
                }
                catch (Exception ex)
                {
                    return Unauthorized();
                }
            }
            return BadRequest(ModelState); 
        }

    }
}