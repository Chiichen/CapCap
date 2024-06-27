package cn.chiichen.gamevibes.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cn.chiichen.game.UserRepository
import cn.chiichen.gamevibes.config.GameVibesConfig
import org.casdoor.Casdoor
import org.casdoor.CasdoorConfig

class LoginViewModel : ViewModel() {
    val userRepository = UserRepository();
    val casdoorConfig = CasdoorConfig(
        endpoint = GameVibesConfig.casdoor_endpoint,
        clientID = GameVibesConfig.casdoor_clientID,
        organizationName = GameVibesConfig.casdoor_organizationName,
        redirectUri = GameVibesConfig.casdoor_redirectUri,
        appName = GameVibesConfig.casdoor_appName,
    )
    val casdoor = Casdoor(casdoorConfig)
    val accessToken: MutableState<String?> = mutableStateOf(null);
    fun getAccessToken(code: String) {
//        accessToken.value = casdoor.requestOauthAccessToken(code).accessToken
//        viewModelScope.launch {
//            accessToken.value = userRepository.getTokenDetail(code)?.access_token
//        }
        accessToken.value =
            "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImNlcnQtYnVpbHQtaW4iLCJ0eXAiOiJKV1QifQ.eyJvd25lciI6IkdhbWVWaWJlIiwibmFtZSI6InpoZW5namllY29uZyIsImNyZWF0ZWRUaW1lIjoiMjAyNC0wNi0wM1QxNDoxNDo1MiswODowMCIsInVwZGF0ZWRUaW1lIjoiMjAyNC0wNi0yNFQxMDo1Mjo0OVoiLCJkZWxldGVkVGltZSI6IiIsImlkIjoiZDhiNzIyNDktOTdlNy00OGQ5LWE4NGMtNGRjNjAyZWJkYjU0IiwidHlwZSI6Im5vcm1hbC11c2VyIiwicGFzc3dvcmQiOiIiLCJwYXNzd29yZFNhbHQiOiIiLCJwYXNzd29yZFR5cGUiOiJzaGE1MTItc2FsdCIsImRpc3BsYXlOYW1lIjoiR2FtZVZpYmVzLWFkbWluIiwiZmlyc3ROYW1lIjoiIiwibGFzdE5hbWUiOiIiLCJhdmF0YXIiOiJodHRwczovL2Nkbi5jYXNiaW4ub3JnL2ltZy9jYXNiaW4uc3ZnIiwiYXZhdGFyVHlwZSI6IiIsInBlcm1hbmVudEF2YXRhciI6IiIsImVtYWlsIjoiNmNhZDBkQGV4YW1wbGUuY29tIiwiZW1haWxWZXJpZmllZCI6ZmFsc2UsInBob25lIjoiMDY4MjY4Njc2MDgiLCJjb3VudHJ5Q29kZSI6IlVTIiwicmVnaW9uIjoiIiwibG9jYXRpb24iOiIiLCJhZGRyZXNzIjpbXSwiYWZmaWxpYXRpb24iOiJFeGFtcGxlIEluYy4iLCJ0aXRsZSI6IiIsImlkQ2FyZFR5cGUiOiIiLCJpZENhcmQiOiIiLCJob21lcGFnZSI6IiIsImJpbyI6IiIsImxhbmd1YWdlIjoiIiwiZ2VuZGVyIjoiIiwiYmlydGhkYXkiOiIiLCJlZHVjYXRpb24iOiIiLCJzY29yZSI6MjAwMCwia2FybWEiOjAsInJhbmtpbmciOjIsImlzRGVmYXVsdEF2YXRhciI6ZmFsc2UsImlzT25saW5lIjpmYWxzZSwiaXNBZG1pbiI6dHJ1ZSwiaXNGb3JiaWRkZW4iOmZhbHNlLCJpc0RlbGV0ZWQiOmZhbHNlLCJzaWdudXBBcHBsaWNhdGlvbiI6IkdhbWVWaWJlIiwiaGFzaCI6IiIsInByZUhhc2giOiIiLCJhY2Nlc3NLZXkiOiIiLCJhY2Nlc3NTZWNyZXQiOiIiLCJnaXRodWIiOiIiLCJnb29nbGUiOiIiLCJxcSI6IiIsIndlY2hhdCI6IiIsImZhY2Vib29rIjoiIiwiZGluZ3RhbGsiOiIiLCJ3ZWlibyI6IiIsImdpdGVlIjoiIiwibGlua2VkaW4iOiIiLCJ3ZWNvbSI6IiIsImxhcmsiOiIiLCJnaXRsYWIiOiIiLCJjcmVhdGVkSXAiOiIiLCJsYXN0U2lnbmluVGltZSI6IiIsImxhc3RTaWduaW5JcCI6IiIsInByZWZlcnJlZE1mYVR5cGUiOiIiLCJyZWNvdmVyeUNvZGVzIjpudWxsLCJ0b3RwU2VjcmV0IjoiIiwibWZhUGhvbmVFbmFibGVkIjpmYWxzZSwibWZhRW1haWxFbmFibGVkIjpmYWxzZSwibGRhcCI6IiIsInByb3BlcnRpZXMiOnt9LCJyb2xlcyI6W10sInBlcm1pc3Npb25zIjpbXSwiZ3JvdXBzIjpbXSwibGFzdFNpZ25pbldyb25nVGltZSI6IiIsInNpZ25pbldyb25nVGltZXMiOjAsInRva2VuVHlwZSI6ImFjY2Vzcy10b2tlbiIsInRhZyI6InN0YWZmIiwic2NvcGUiOiJwcm9maWxlIiwiaXNzIjoiaHR0cHM6Ly9hdXRoLmNoaWljaGVuLmNuIiwic3ViIjoiZDhiNzIyNDktOTdlNy00OGQ5LWE4NGMtNGRjNjAyZWJkYjU0IiwiYXVkIjpbImU2MDViZDc4OWEwODM2ZjIyMTg2Il0sImV4cCI6MTcxOTkwNzY2NywibmJmIjoxNzE5MzAyODY3LCJpYXQiOjE3MTkzMDI4NjcsImp0aSI6ImFkbWluL2E3ZjQ0ZjhjLWJiZDEtNDdmYS05ZWQ5LTA4ODJhNTQ2ODg0MCJ9.cIpMr4VhvZ8Cfa9cAITCWaci9NZo65jfH7mgC_oqbXpMcKGv-2orEGApkh4I2zKqtUdl6THgE9l0twBONgfY7MnNAAsxloNcvQqbdasq_NAzLaomd7A6NS37OHGZO9f3SE5O3sl4_b1XMH9EnqWmvF3kTAj7_krcjjoAKBZA6uw48UdmSbt2ZexfajYA9Q7dAEuXshFmI9e3I52LnJBD9xFh0jD7mygKAPXNiO0czl8SCCRPo6ho4aO3KFso81I8q5_-Txa1CaAeT4vd-yjpHLXgxlWDhVmpI0dII08Fhlx7CEpuuA7Zh2lwMkNiRL_vvD8I6xZeM40nkRsTr6hQGBcmmAVV17YRJe_a_6FHxVlQm1ceR8fC8a5IZWpHKaRS5JoRKhr3xUctYJntEVwXD60pCpCYoEBmrSx-KmBuuQ6RWg1mVf37o9BrvRFknIJH3-UdKKEMA4xyXhC9lAkds5h7TgdRSddCuNKgC0VcMgPXyEc7uz4uovhWaC5V0wzWAXcx4j6T_TFu1_R-OMgOpWW566b2Mr_V8fMIWxHUpp2N_cQKRVnRp_nZJcs8_HSL5FCK0bQSf70oLtpCchNi-7-3tpKctRSZyFv5XuYxvpBNuv2j2OEPxJ8lO5rPJumQ568VaIIbBhsotAt9Oku3Dmdc8w3Hce8DwRalL3qfdbs";
    }

}