export interface JwtPayloadDto {
    sub: string;
    scope: string;
    iss: string;
    fullname: string;
    exp: number;
    iat: number;
    jti: string;
}